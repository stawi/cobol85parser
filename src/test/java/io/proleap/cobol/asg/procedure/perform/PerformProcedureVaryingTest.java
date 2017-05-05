package io.proleap.cobol.asg.procedure.perform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import io.proleap.cobol.CobolTestSupport;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.Statement;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.perform.By;
import io.proleap.cobol.asg.metamodel.procedure.perform.From;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformProcedureStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformType;
import io.proleap.cobol.asg.metamodel.procedure.perform.TestClause;
import io.proleap.cobol.asg.metamodel.procedure.perform.Until;
import io.proleap.cobol.asg.metamodel.procedure.perform.Varying;
import io.proleap.cobol.asg.metamodel.procedure.perform.VaryingClause;
import io.proleap.cobol.asg.metamodel.procedure.perform.VaryingPhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ConditionValueStmt;
import io.proleap.cobol.asg.runner.impl.CobolParserRunnerImpl;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

public class PerformProcedureVaryingTest extends CobolTestSupport {

	@Test
	public void test() throws Exception {
		final File inputFile = new File(
				"src/test/resources/io/proleap/cobol/asg/procedure/perform/PerformProcedureVarying.cbl");
		final Program program = new CobolParserRunnerImpl().analyzeFile(inputFile, CobolSourceFormatEnum.TANDEM);

		final CompilationUnit compilationUnit = program.getCompilationUnit("PERFORMPROCEDUREVARYING");
		final ProgramUnit programUnit = compilationUnit.getProgramUnit();
		final ProcedureDivision procedureDivision = programUnit.getProcedureDivision();
		assertEquals(0, procedureDivision.getParagraphs().size());
		assertEquals(1, procedureDivision.getStatements().size());

		{
			final Statement statement = procedureDivision.getStatements().get(0);
			assertEquals(StatementTypeEnum.PERFORM, statement.getStatementType());

			final PerformStatement performStatement = (PerformStatement) statement;
			assertEquals(PerformStatement.Type.PROCEDURE, performStatement.getType());

			{
				final PerformProcedureStatement performProcedureStatement = performStatement
						.getPerformProcedureStatement();
				assertNotNull(performProcedureStatement.getPerformType());

				{
					final PerformType performType = performProcedureStatement.getPerformType();
					assertEquals(PerformType.Type.VARYING, performType.getType());

					{
						final Varying varying = performType.getVarying();

						{
							assertNotNull(varying.getTestClause());
							assertEquals(TestClause.Type.BEFORE, varying.getTestClause().getType());
						}

						{
							final VaryingClause varyingClause = varying.getVaryingClause();

							{
								final VaryingPhrase varyingPhrase = varyingClause.getVaryingPhrase();

								{
									final From from = varyingPhrase.getFrom();
									assertEquals(1, from.getFromValueStmt().getValue());
								}

								{
									final By by = varyingPhrase.getBy();
									assertEquals(2, by.getByValueStmt().getValue());
								}

								{
									final Until until = varyingPhrase.getUntil();
									assertNotNull(until);

									final ConditionValueStmt condition = until.getCondition();
									assertNotNull(condition);
								}
							}
						}
					}
				}
			}
		}
	}
}